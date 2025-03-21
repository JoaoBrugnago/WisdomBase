package com.wisdom.repositories;

import com.wisdom.models.Arquivo;
import com.wisdom.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoRepository {

    public void cadastrarArquivo(Arquivo arquivo) {
        String insert = "insert into Arquivos (ArquivoNome, ArquivoTipo, ArquivoTamanho, ArquivoConteudo, ArquivoCaminho, PastaIdSequencia, UsuarioIdSequencia) values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert)) {

            pst.setString(1, arquivo.getNome());
            pst.setString(2, arquivo.getTipo());
            pst.setLong(3, arquivo.getTamanho());
            pst.setBytes(4, arquivo.getConteudo());
            pst.setString(5, arquivo.getCaminho());
            pst.setInt(6, arquivo.getPastaId());
            pst.setInt(7, arquivo.getUsuarioId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum arquivo criada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar arquivo.", e);
        }
    }

    public Arquivo recuperarArquivoViaId(int arquivoId) {
        String read = "select * from Arquivos where ArquivoIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setInt(1, arquivoId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToArquivo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar arquivo.", e);
        }
        return null;
    }

    public void excluirArquivo(int idArquivo) {
        String delete = "delete from Arquivos where ArquivoIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(delete)) {

            pst.setInt(1, idArquivo);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum arquivo excluído.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir arquivo.", e);
        }
    }

    public List<Arquivo> recuperarArquivosViaIdPasta(int pastaId) {
        String read = "select * from Arquivos where PastaIdSequencia = ?";
        List<Arquivo> arquivos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setInt(1, pastaId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    arquivos.add(mapResultSetToArquivo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar arquivos por ID de Pasta.", e);
        }

        return arquivos;
    }

    private Arquivo mapResultSetToArquivo(ResultSet rs) throws SQLException {
        return new Arquivo(
                rs.getInt("ArquivoIdSequencia"),
                rs.getString("ArquivoNome"),
                rs.getString("ArquivoTipo"),
                rs.getLong("ArquivoTamanho"),
                rs.getBytes("ArquivoConteudo"),
                rs.getString("ArquivoCaminho"),
                rs.getTimestamp("ArquivoDataCriacao"),
                rs.getInt("PastaIdSequencia"),
                rs.getInt("UsuarioIdSequencia")
        );
    }
}
