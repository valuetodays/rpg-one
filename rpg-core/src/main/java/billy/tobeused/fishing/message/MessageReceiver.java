package billy.tobeused.fishing.message;

import org.apache.log4j.Logger;

/**
 * @author liulei-home
 * @since 2018-06-10 09:45
 */
public abstract class MessageReceiver {
    protected Logger LOG = Logger.getLogger(getClass());

    public abstract void doReceive(Object o);
}
