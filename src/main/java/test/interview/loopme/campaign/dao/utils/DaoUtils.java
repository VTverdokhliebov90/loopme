package test.interview.loopme.campaign.dao.utils;


import java.util.List;
import java.util.Optional;

public final class DaoUtils {

    public static boolean isResultAvailable(List list) {
        return !Optional.ofNullable(list).map(List::isEmpty).orElse(true);
    }
}
