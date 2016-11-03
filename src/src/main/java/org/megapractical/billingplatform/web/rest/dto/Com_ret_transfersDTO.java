package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Com_local_retentions;
import org.megapractical.billingplatform.domain.Com_local_transfered;

public class Com_ret_transfersDTO {

    private Com_local_retentions retentions;
    private Com_local_transfered transfered;

    public Com_local_retentions getRetentions() {
        return retentions;
    }

    public void setRetentions(Com_local_retentions retentions) {
        this.retentions = retentions;
    }

    public Com_local_transfered getTransfered() {
        return transfered;
    }

    public void setTransfered(Com_local_transfered transfered) {
        this.transfered = transfered;
    }
}
