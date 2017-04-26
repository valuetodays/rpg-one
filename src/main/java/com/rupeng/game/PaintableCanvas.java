package com.rupeng.game;

import java.awt.Graphics;

import javax.swing.JPanel;

class PaintableCanvas extends JPanel {
    private static final long serialVersionUID = 1L;
    private PaintListener paintListener;

	public void setPaintListener(PaintListener l) {
		this.paintListener = l;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(paintListener!=null)
		{
			paintListener.paintComponent(g);
		}
	}
}
