package com.wisdom.repositories;

import com.wisdom.models.Pasta;
import com.wisdom.utils.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PastaRepository {

    public Pasta recuperarPastaViaId(int idPasta) {
        String read = "select * from Pastas where PastaIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setInt(1, idPasta);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPasta(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar pasta.", e);
        }
        return null;
    }

    public List<Pasta> recuperarPastasFilhasViaIdPai(int idPastaPai) {
        String read = "select * from Pastas where PastaNroPai = ?";
        List<Pasta> pastas = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setInt(1, idPastaPai);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    pastas.add(mapResultSetToPasta(rs));
                }
            }

            return pastas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar pastas filhas.", e);
        }
    }

    public void criarPasta(String nomePasta, int idPastaPai, int idUsuario) {
        String update = "insert into Pastas (PastaNome, PastaNroPai, UsuarioIdSequencia) values (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setString(1, nomePasta);
            pst.setInt(2, idPastaPai);
            pst.setInt(3, idUsuario);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhuma pasta criada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pasta.", e);
        }
    }

    public void excluirPasta(int idPasta) {
        String update = "delete from Pastas where PastaIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setInt(1, idPasta);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhuma pasta excluída.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pasta.", e);
        }
    }

    public void renomearPasta(int idPasta, String novoNome) {
        String query = "update Pastas set PastaNome = ? where PastaIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, novoNome);
            pst.setInt(2, idPasta);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Erro: Nenhuma pasta foi renomeada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao renomear pasta.", e);
        }
    }

    public List<String> recuperarCaminhoPaisViaId(int id) {
        List<String> caminho = new ArrayList<>();
        Pasta pasta = recuperarPastaViaId(id);

        while (pasta.getIdPai() != null) {
            caminho.add(pasta.getNome());
            pasta = (pasta.getIdPai() != null) ? recuperarPastaViaId(pasta.getIdPai()) : null;
        }

        caminho.add("Root");
        Collections.reverse(caminho);
        return caminho;
    }

    private Pasta mapResultSetToPasta(ResultSet rs) throws SQLException {
        return new Pasta(
                rs.getInt("PastaIdSequencia"),
                rs.getString("PastaNome"),
                rs.getString("PastaStatus"),
                rs.getObject("PastaNroPai") != null ? rs.getInt("PastaNroPai") : null,
                rs.getTimestamp("PastaDataCriacao"),
                rs.getTimestamp("PastaDataUltimaModificacao"),
                rs.getInt("UsuarioIdSequencia")
        );
    }
}
