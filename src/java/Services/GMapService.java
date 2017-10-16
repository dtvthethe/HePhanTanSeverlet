/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Bean.UserTracking;
import Configs.Config;

/**
 *
 * @author Admin
 */
public class GMapService {
    public static String getUrl(UserTracking ut){
        return "https://maps.googleapis.com/maps/api/staticmap?center="+ut.getLocationX()+","+ut.getLocationY()+"&zoom="+ut.getZoom()+"&size="+ut.getSize()+"x"+ut.getSize()+"&markers=color:red%7Clabel:H%7C"+ut.getLocationX()+","+ut.getLocationY()+"&key="+Config.GMAP_API_KEY;
    }
}
