typedef enum
  {
    SANE_STATUS_GOOD = 0,       /* everything A-OK */
    SANE_STATUS_UNSUPPORTED,    /* operation is not supported */
    SANE_STATUS_CANCELLED,      /* operation was cancelled */
    SANE_STATUS_DEVICE_BUSY,    /* device is busy; try again later */
    SANE_STATUS_INVAL,          /* data is invalid (includes no dev at open) */
    SANE_STATUS_EOF,            /* no more data available (end-of-file) */
    SANE_STATUS_JAMMED,         /* document feeder jammed */
    SANE_STATUS_NO_DOCS,        /* document feeder out of documents */
    SANE_STATUS_COVER_OPEN,     /* scanner cover is open */
    SANE_STATUS_IO_ERROR,       /* error during device I/O */
    SANE_STATUS_NO_MEM,         /* out of memory */
    SANE_STATUS_ACCESS_DENIED   /* access to resource has been denied */
  }
SANE_Status;

SANE_Status getGoodValue();
SANE_Status getSomeError();
