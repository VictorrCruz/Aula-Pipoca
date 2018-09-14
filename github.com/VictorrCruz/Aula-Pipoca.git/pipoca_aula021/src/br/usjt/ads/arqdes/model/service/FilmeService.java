package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;

public class FilmeService {
	private FilmeDAO dao;
	
	public FilmeService() {
		dao = new FilmeDAO();
	}
	
	public Filme buscarFilme(int id) throws IOException{
		return dao.buscarFilme(id);
	}
	
	public Filme inserirFilme(Filme filme) throws IOException {
		int id = dao.inserirFilme(filme);
		filme.setId(id);
		return filme;
	}

	public ArrayList<Filme> listarFilmes(String chave) throws IOException{
		return dao.listarFilmes(chave);
	}

	public ArrayList<Filme> listarFilmes() throws IOException{
		return dao.listarFilmes();
		
		
	}

	public void atualizar(Filme filme) throws Exception{
		dao.atualizar(filme);
	}
	
	public void excluir(int id) throws Exception {
		dao.excluir(id);
	}
     public void carregar (int id)   throws Exception{
    	 dao.carregar(id);
;    	 
     }

	public Filme carregar(Filme filme) {
		// TODO Auto-generated method stub
		return null;
	}
}
