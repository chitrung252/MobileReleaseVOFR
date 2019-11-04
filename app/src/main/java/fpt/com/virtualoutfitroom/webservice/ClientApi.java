package fpt.com.virtualoutfitroom.webservice;

import fpt.com.virtualoutfitroom.repository.VofrService;

public class ClientApi extends BaseApi {
    public VofrService rmapService(){
        return this.getService(VofrService.class, ConfigApi.BASE_URL);
    }
}
