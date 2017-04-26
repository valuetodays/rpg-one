package com.billy.scriptParser.loader;

import java.util.List;

import com.billy.scriptParser.bean.LoaderBean;

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
public interface ILoader extends Loadable {

    List<LoaderBean> load() throws Exception;
    
}
