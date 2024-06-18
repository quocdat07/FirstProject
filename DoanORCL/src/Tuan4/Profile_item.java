/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan4;

/**
 *
 * @author lemin
 */
public class Profile_item {
    public String profile , name ,value;

    public Profile_item(String profile, String name, String value) {
        this.profile = profile;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
