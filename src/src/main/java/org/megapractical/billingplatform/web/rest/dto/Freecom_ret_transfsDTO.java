package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Freecom_local_retentions;
import org.megapractical.billingplatform.domain.Freecom_local_transfered;


public class Freecom_ret_transfsDTO {

    private Freecom_local_retentions retentions;
    private Freecom_local_transfered transfered;

    public Freecom_local_retentions getRetentions() {
        return retentions;
    }

    public void setRetentions(Freecom_local_retentions retentions) {
        this.retentions = retentions;
    }

    public Freecom_local_transfered getTransfered() {
        return transfered;
    }

    public void setTransfered(Freecom_local_transfered transfered) {
        this.transfered = transfered;
    }
}
