package com.sf.importacao;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

import com.sf.model.MovimentacaoBancaria;


// Classe para importar o OFX
public class OFXImport {

	public List<MovimentacaoBancaria> importarOFX(File arquivo) {
		List<MovimentacaoBancaria> movimentacoes = new ArrayList<>();
		
	    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
	        String linha;
	        boolean emTransacao = false;

	        String tipo = "", data = "", valor = "", memo = "";

	        while ((linha = br.readLine()) != null) {
	            linha = linha.trim();

	            if (linha.startsWith("<STMTTRN>")) {
	                emTransacao = true;
	            } else if (linha.startsWith("</STMTTRN>")) {
	                emTransacao = false;
	                
	                //Seta os valores na classe
	                MovimentacaoBancaria mov = new MovimentacaoBancaria();
                    mov.setTipoMov(tipo);
                    mov.setDescMov(memo);
                    mov.setValorMov(Float.parseFloat(valor));
                   	mov.setDataMov(parseData(data));
                    
                    // talvez mude
                    mov.setIdMov(0); 
                    mov.setIdClassificacao(0);
                    mov.setIdConta(null);
                    mov.setIdFornecedor(null);
                    
                    movimentacoes.add(mov);
                    
	                // Reset para a próxima transação
	                tipo = ""; data = ""; valor = ""; memo = "";
	            }

	            if (emTransacao) {
	                if (linha.startsWith("<TRNTYPE>")) {
	                    tipo = linha.replace("<TRNTYPE>", "").trim();
	                    tipo = tipo.replace("</TRNTYPE>", "");
	                    
	                } else if (linha.startsWith("<DTPOSTED>")) {
	                    data = linha.replace("<DTPOSTED>", "").trim();
	                    data = data.replace("</DTPOSTED>", "").trim();
	                    
	                } else if (linha.startsWith("<TRNAMT>")) {
	                    valor = linha.replace("<TRNAMT>", "").trim();
	                    valor = valor.replace("</TRNAMT>", "").trim();
	                    
	                } else if (linha.startsWith("<MEMO>")) {
	                    memo = linha.replace("<MEMO>", "").trim();
	                    memo = memo.replace("</MEMO>", "").trim();
	                    
	                }
	            }
	        }

	        JOptionPane.showMessageDialog(null, "Arquivo importado com sucesso!", "Importação", JOptionPane.INFORMATION_MESSAGE);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    return movimentacoes;
	}
	
	// Formata data OFX (20240601120000[-3:GMT] → 01/06/2024)
    private Date parseData(String rawData) {
        if (rawData.length() >= 8) {
        	try {
                SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
                // Converte para java.sql.Date e retorna
                return new java.sql.Date(formato.parse(rawData).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        	
        }
        return null;
    }
}
