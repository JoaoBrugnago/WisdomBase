package com.wisdom.repositories;

import com.wisdom.models.Token;
import com.wisdom.utils.DatabaseConnection;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;

public class TokenRepository {
    private UsuarioRepository usuarioRepository;

    public TokenRepository() {
        usuarioRepository = new UsuarioRepository();
    }

    public void cadastrarToken(String token, String chave, int usuarioId) {
        String update = "insert into Tokens (TokenConteudo, TokenTipoChave, UsuarioIdSequencia) values (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setString(1, token);
            pst.setString(2, chave);
            pst.setInt(3, usuarioId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum token cadastrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar token.", e);
        }
    }

    public Token retornarToken(String token) {
        String read = "select * from Tokens where TokenConteudo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, token);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Token(
                            rs.getString("TokenConteudo"),
                            rs.getString("TokenTipoChave"),
                            usuarioRepository.recuperarUsuarioViaId(rs.getInt("UsuarioIdSequencia")),
                            rs.getTimestamp("TokenDataCriacao")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar token.", e);
        }
        return null;
    }

    public boolean validarToken(String token) {
        String read = "select 1 from Tokens where TokenConteudo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, token);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar token.", e);
        }
    }

    public boolean validarTokenComDuracaoDeUmDia(String token) {
        String read = "select * from Tokens where TokenConteudo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, token);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Timestamp dataCriacao = rs.getTimestamp("TokenDataCriacao");
                    Instant agora = Instant.now();
                    long horasDeDiferenca = Duration.between(dataCriacao.toInstant(), agora).toHours();
                    if (horasDeDiferenca > 24) {
                        return false; // Token expirado
                    }
                    return true; // Token válido
                } else {
                    return false; // Token não encontrado
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar token.", e);
        }
    }
}
