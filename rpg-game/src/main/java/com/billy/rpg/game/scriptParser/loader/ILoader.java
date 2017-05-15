package com.billy.rpg.game.scriptParser.loader;

import com.billy.rpg.game.scriptParser.bean.LoaderBean;

import java.util.List;

/**
 *                                                                 | -- IDataLoader
 * ILoader  ---- |--   IResourceLoader  -- |  
 *                                                                 | -- IImageLoader
 *                       
 *
 * @author liulei@foorich.com
 * 
 * @since 2016-11-30 17:26:02
 */
public interface ILoader {

    List<LoaderBean> load() throws Exception;
    
}
