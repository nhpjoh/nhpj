package NLL_migration.skapaTestdata;

import org.junit.Test;

public class InnerClassTest {

    class Inner {
        private Integer siffra = null;
        private String text = null;

        Inner( Integer siffra, String text) { setSiffra(siffra); setText(text);}
        public Integer getSiffra() { return siffra; }
        public void setSiffra(Integer siffra) { this.siffra = siffra; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        @Override
        public String toString() { return getSiffra() + " : " + getText();}
    }


    @Test
    public void test() {
        Inner inner = new Inner(5,"apa");
        System.out.println(inner);
    }
}
