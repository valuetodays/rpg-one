package billy.tobeused.fishing;

import billy.tobeused.fishing.message.MessageReceiver;

/**
 * @author liulei-home
 * @since 2018-06-10 09:38
 */
public class FishingMessageReceiver extends MessageReceiver<FishType> {

    /**
     * handle the message that get a new fish,
     * i.e., to increase the number of fish in package
     *
     * @param fishType fishType
     */
    @Override
    public void doReceive(FishType fishType) {
        LOG.info("do not forget to increase the number of fish in package!!!!");
    }
}
