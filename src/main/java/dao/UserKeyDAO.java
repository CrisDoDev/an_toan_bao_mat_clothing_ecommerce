package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.UserKey;
import util.DBContext;

public class UserKeyDAO {

    public void insertKey(UserKey key) {
        String sql = "INSERT INTO UserKeys (user_id, public_key_text, status) VALUES (?, ?, 'ACTIVE')";
        try (Connection conn = DBContext.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, key.getUserId());
            ps.setString(2, key.getPublicKeyText());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserKey getLatestActiveKey(int userId) {
        String sql = "SELECT TOP 1 * FROM UserKeys WHERE user_id = ? AND status = 'ACTIVE' ORDER BY created_at DESC";
        try (Connection conn = DBContext.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserKey key = new UserKey();
                    key.setKeyId(rs.getInt("key_id"));
                    key.setUserId(rs.getInt("user_id"));
                    key.setPublicKeyText(rs.getString("public_key_text"));
                    key.setCreatedAt(rs.getTimestamp("created_at"));
                    key.setRevokedAt(rs.getTimestamp("revoked_at"));
                    key.setStatus(rs.getString("status"));
                    return key;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void revokeKey(int userId) {
        String sql = "UPDATE UserKeys SET status = 'REVOKED', revoked_at = GETDATE() WHERE user_id = ? AND status = 'ACTIVE'";
        try (Connection conn = DBContext.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserKey> getAllKeysByUserId(int userId) {
        List<UserKey> list = new ArrayList<>();
        String sql = "SELECT * FROM UserKeys WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBContext.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserKey key = new UserKey();
                    key.setKeyId(rs.getInt("key_id"));
                    key.setUserId(rs.getInt("user_id"));
                    key.setPublicKeyText(rs.getString("public_key_text"));
                    key.setCreatedAt(rs.getTimestamp("created_at"));
                    key.setRevokedAt(rs.getTimestamp("revoked_at"));
                    key.setStatus(rs.getString("status"));
                    list.add(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}