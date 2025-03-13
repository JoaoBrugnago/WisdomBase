package com.wisdom.repositories;

import com.wisdom.models.Pasta;
import com.wisdom.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PastaRepository {

    public Pasta recuperarPastaViaId(int id) {
        String read = "select * from Pastas where PastaIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Pasta(
                            rs.getInt("PastaIdSequencia"),
                            rs.getString("PastaNome"),
                            rs.getString("PastaStatus"),
                            rs.getInt("PastaNroPai"),
                            rs.getTimestamp("PastaDataCriacao"),
                            rs.getTimestamp("PastaDataUltimaModificacao"),
                            rs.getInt("UsuarioIdSequencia")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar pasta.", e);
        }
        return null;
    }
}
