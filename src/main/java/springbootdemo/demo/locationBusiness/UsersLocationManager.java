package springbootdemo.demo.locationBusiness;

import springbootdemo.demo.locationBusiness.data.DataProvider;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;

public class UsersLocationManager {

    private IDataProvider dataProvider;

    public UsersLocationManager() {
        dataProvider = new DataProvider();
    }

    public void addNewLocation(UncheckedUserLocation uncheckedUserLocation) {

    }
}
