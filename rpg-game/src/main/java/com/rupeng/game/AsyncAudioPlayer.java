package com.rupeng.game;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Player有内存泄露造成AsyncAudioPlayer也有内存泄露
 * @author yzk
 *
 */
public class AsyncAudioPlayer
{
	private Player player = null;
	
	private boolean isRepeat;
	private boolean isClosed = false;
	
	private byte[] audioBytes;

	public AsyncAudioPlayer(String audioFilePath, boolean isRepeat)
	{
		this.isRepeat = isRepeat;
		try
		{
			this.audioBytes = GameUtils.readAllBytes(new File(audioFilePath));
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		
	}
	
	private void doPlay()
	{
		try
		{
			if(player!=null)
			{
				player.close();//先把之前的实例释放
				player = null;
			}
			ByteArrayInputStream bytesStream = new ByteArrayInputStream(this.audioBytes);
			player = new Player(bytesStream);
			player.play();
		} catch (JavaLayerException e)
		{
			throw new RuntimeException("播放器异常", e);
		}
	}

	public void playAsync()
	{
		//Player的播放时同步的，因此放到线程中进行异步播放
		Thread playThread = new Thread(new Runnable() {
			public void run()
			{				
				doPlay();
				if(!isRepeat)//如果不是重复播放，则播放一次即可
				{
					return;
				}
				while(true)//重复播放
				{
					if(isClosed)//如果已经关闭则结束
					{
						break;
					}
					if(player.isComplete())//如果播放结束了，则播放完成再播放一遍
					{
						doPlay();
					}
					GameUtils.pause(100);//暂停100毫秒，避免占CPU
				}
			}
		});
		playThread.setDaemon(true);
		playThread.start();
	}
	
	public void close()
	{
		this.isClosed = true;
		if(player!=null)
		{
			player.close();//先把之前的实例释放
			player = null;
		}
		
	}
}
