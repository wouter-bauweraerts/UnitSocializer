package io.github.wouterbauweraerts.unitsocializer.core.util;

import org.mockito.MockingDetails;

import static org.mockito.Mockito.mockingDetails;

public class MockUtil {
    public static boolean isMock(Object object) {
        MockingDetails md = mockingDetails(object);

        return !md.isSpy() && md.isMock();
    }

    public static boolean isSpy(Object object) {
        MockingDetails md = mockingDetails(object);

        return md.isSpy();
    }
}
