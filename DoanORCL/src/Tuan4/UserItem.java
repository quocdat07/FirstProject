/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan4;

/**
 *
 * @author lemin
 */
public class UserItem {
    public  String  UserName , De_Tablespace , profile ;

    public UserItem(String UserName, String De_Tablespace, String profile) {
        this.UserName = UserName;
        this.De_Tablespace = De_Tablespace;
        this.profile = profile;
    }

    @Override
    public String toString() {
       return this.UserName;
    }
    
}
