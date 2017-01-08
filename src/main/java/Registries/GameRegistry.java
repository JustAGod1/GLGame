package Registries;

import Objects.Building;
import Objects.HUDElement;
import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yuri on 04.01.17.
 */
public class GameRegistry {
    private static HashMap<String, HUDElement> hudElements = new HashMap<>();
    private static ArrayList<HUDElement> clickableHUDElements = new ArrayList<>();

    private static HashMap<String, Building> buildings = new HashMap<>();

    public static void registerBuilding(Building building, String id) {
        buildings.put(id, building);
    }

    public static void registerHUDElement(HUDElement element, String id) {
        hudElements.put(id, element);
        if (element.isClickable()) {
            clickableHUDElements.add(element);
        }
    }

    public static void onDrawHUDElements(GL2 gl2) {
        for (HUDElement element : hudElements.values()) {
            element.drawAt(gl2);
        }
    }
}
