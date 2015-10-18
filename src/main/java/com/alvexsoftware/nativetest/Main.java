package com.alvexsoftware.nativetest;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author anganar
 */
public class Main {
    
    public static interface SANE_Status {
        public static final int SANE_STATUS_GOOD = 0;           /* everything A-OK */
        public static final int SANE_STATUS_UNSUPPORTED = 1;    /* operation is not supported */
        public static final int SANE_STATUS_CANCELLED = 2;      /* operation was cancelled */
        public static final int SANE_STATUS_DEVICE_BUSY = 3;    /* device is busy; try again later */
        public static final int SANE_STATUS_INVAL = 4;          /* data is invalid (includes no dev at open) */
        public static final int SANE_STATUS_EOF = 5;            /* no more data available (end-of-file) */
        public static final int SANE_STATUS_JAMMED = 6;         /* document feeder jammed */
        public static final int SANE_STATUS_NO_DOCS = 7;        /* document feeder out of documents */
        public static final int SANE_STATUS_COVER_OPEN = 8;     /* scanner cover is open */
        public static final int SANE_STATUS_IO_ERROR = 9;       /* error during device I/O */
        public static final int SANE_STATUS_NO_MEM = 10;        /* out of memory */
        public static final int SANE_STATUS_ACCESS_DENIED = 11; /* access to resource has been denied */
    }

    public interface TestLibrary extends Library {

        TestLibrary INSTANCE = (TestLibrary)
            Native.loadLibrary((Platform.isWindows()
                    ? "bla"
                    : "/home/anganar/git/other/jna-sane-playground/nativeCode/libtest.so"),
                TestLibrary.class);
        int getGoodValue();
        int getSomeError();
        
    }
    
    public interface SaneLibrary extends Library {
        
        public static class DevStruct extends Structure {

            @Override
            protected List getFieldOrder() {
                 return Arrays.asList(new String[] { "name", "vendor", "model", "type" });
            }
            public static class ByReference extends DevStruct implements Structure.ByReference {}

            public String name;
            public String vendor;
            public String model;
            public String type;

            public DevStruct() {}
            public DevStruct(Pointer p) {
                    super(p);
                    read();
            }
	}
        
        SaneLibrary INSTANCE = (SaneLibrary)
            Native.loadLibrary((Platform.isWindows() ? "sane" : "sane"),
                               SaneLibrary.class);

        int sane_init( IntByReference versionCode, PointerByReference authCallback );
        int sane_get_devices( PointerByReference deviceListPointer, boolean localOnly );
        int sane_open( String deviceName, PointerByReference handle );
        void sane_close( Pointer handle );
        void sane_exit();
    }
    
    protected static String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case SANE_Status.SANE_STATUS_GOOD:
                return "Everything ok";
            case SANE_Status.SANE_STATUS_INVAL:
                return "Data is invalid or no device at open";
            // TODO: map all statuses
        }
        return "Unknown error";
    }
    
    public static void main(String[] args) {
        SaneLibrary sane = SaneLibrary.INSTANCE;
        sane.sane_init(null, null);
        PointerByReference pref = new PointerByReference();
        int status = sane.sane_get_devices(pref, false);
        Pointer returned = pref.getValue();
        Pointer[] array = returned.getPointerArray(0);
        for (Pointer p : array) {
            SaneLibrary.DevStruct dev = new SaneLibrary.DevStruct(p);
            System.out.println(dev.model);
        }
        
        TestLibrary test = TestLibrary.INSTANCE;
        int testStatus = test.getGoodValue();
        if( testStatus == SANE_Status.SANE_STATUS_GOOD )
            System.out.println("Status is good");
        testStatus = test.getSomeError();
        if( testStatus != SANE_Status.SANE_STATUS_GOOD )
            System.out.println("Status is bad. Error: " + getErrorMessage(testStatus));
    }
}
