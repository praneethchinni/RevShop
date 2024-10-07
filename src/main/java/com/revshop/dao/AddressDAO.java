package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.revshop.model.Address;
import com.revshop.util.DBUtil;

public class AddressDAO {

    public void saveAddress(Address address) {
        String query = "INSERT INTO addresses (user_id, first_name, last_name, door_no, building_name, address, landmark, city, district, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getFirstName());
            ps.setString(3, address.getLastName());
            ps.setString(4, address.getDoorNo());
            ps.setString(5, address.getBuildingName());
            ps.setString(6, address.getAddress());
            ps.setString(7, address.getLandmark());
            ps.setString(8, address.getCity());
            ps.setString(9, address.getDistrict());
            ps.setString(10, address.getPincode());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?"; // Ensure this is the correct query

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id")); // Ensure the ID is being set correctly
                address.setFirstName(rs.getString("first_name"));
                address.setLastName(rs.getString("last_name"));
                address.setDoorNo(rs.getString("door_no"));
                address.setBuildingName(rs.getString("building_name"));
                address.setAddress(rs.getString("address"));
                address.setLandmark(rs.getString("landmark"));
                address.setCity(rs.getString("city"));
                address.setDistrict(rs.getString("district"));
                address.setPincode(rs.getString("pincode"));
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses; // Ensure this returns the correct list
    }


    // New method to get a specific address by its ID
    public Address getAddressById(int addressId) {
        Address address = null;
        String query = "SELECT * FROM addresses WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                address = new Address();
                address.setId(rs.getInt("id"));
                address.setUserId(rs.getInt("user_id"));
                address.setFirstName(rs.getString("first_name"));
                address.setLastName(rs.getString("last_name"));
                address.setDoorNo(rs.getString("door_no"));
                address.setBuildingName(rs.getString("building_name"));
                address.setAddress(rs.getString("address"));
                address.setLandmark(rs.getString("landmark"));
                address.setCity(rs.getString("city"));
                address.setDistrict(rs.getString("district"));
                address.setPincode(rs.getString("pincode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
}
