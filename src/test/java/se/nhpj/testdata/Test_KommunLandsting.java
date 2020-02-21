package se.nhpj.testdata;

import org.junit.Test;

public class Test_KommunLandsting {

    @Test
    public void test_getSlumpad() {
        KommunLandsting kl = new KommunLandsting();
        System.out.println(kl.getSlumpad());
    }
}