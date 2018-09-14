package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

/**
 * Servlet implementation class ManterFilmesController
 */
@WebServlet("/manterfilmes.do")
public class ManterFilmesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String acao = request.getParameter("acao");
		RequestDispatcher dispatcher;
		FilmeService fService = null;
		GeneroService gService;
		Filme filme = null;
		HttpSession session;

		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String diretor = request.getParameter("diretor");
		String posterPath = request.getParameter("posterPath");
		String popularidade = request.getParameter("popularidade") == null
				|| request.getParameter("popularidade").length() == 0 ? "0" : request.getParameter("popularidade");
		String dataLancamento = request.getParameter("dataLancamento") == null
				|| request.getParameter("dataLancamento").length() == 0 ? ""
						: request.getParameter("dataLancamento");
		String idGenero = request.getParameter("genero.id");
		String chave = request.getParameter("data[search]");

		switch (acao) {
		case "novo":
			gService = new GeneroService();
			ArrayList<Genero> generos = gService.listarGeneros();
			session = request.getSession();
			session.setAttribute("generos", generos);
			dispatcher = request.getRequestDispatcher("CriarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "criar":
			

			fService = new FilmeService();
			filme = new Filme();
			filme.setTitulo(titulo);
			filme.setDescricao(descricao);
			filme.setDiretor(diretor);

			gService = new GeneroService();
			Genero genero = new Genero();
			genero.setId(Integer.parseInt(idGenero));
			genero.setNome(gService.buscarGenero(genero.getId()).getNome());
			filme.setGenero(genero);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				filme.setDataLancamento(formatter.parse(dataLancamento));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				filme.setDataLancamento(null);
			}

			filme.setPopularidade(Double.parseDouble(popularidade));
			filme.setPosterPath(posterPath);

			filme = fService.inserirFilme(filme);

			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
	
		case "reiniciar":
			session = request.getSession();
			session.setAttribute("lista", null);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		case "listar":
			session = request.getSession();
			fService = new FilmeService();
			ArrayList<Filme> lista;
			if (chave != null && chave.length() > 0) {
				lista = fService.listarFilmes(chave);
			} else {
				lista = fService.listarFilmes();
			}
			session.setAttribute("lista", lista);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		
		case "visualizar":
			session = request.getSession();
			fService = new FilmeService();
			ArrayList<Filme> lista1;
			if (chave != null && chave.length() > 0) {
				lista1 = fService.listarFilmes(chave);
			} else {
				lista1 = fService.listarFilmes();
			}
			session.setAttribute("lista", lista1);
			dispatcher = request.getRequestDispatcher("VisualizarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		case "editar":
			filme = fService.carregar(filme);
			 request.setAttribute("filme", filme);
			 dispatcher = request.getRequestDispatcher("AlterarFilme.jsp");
			 dispatcher.forward(request, response);     
				break;
				
		case "excluir":
			fService.excluir(filme.getId());
			ArrayList<Filme> filme = (ArrayList<Filme>)session.getAttribute("lista");
			lista.remove(busca(filme, lista));
			session.setAttribute("lista", lista);
			dispatcher = request.getRequestDispatcher("AlterarFilme.jsp");
			 dispatcher.forward(request, response);     
				break;
			
			
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public int busca(ArrayList<Filme> filme, ArrayList<Filme> lista) {
		Filme to;
		for(int i = 0; i < lista.size(); i++){
			to = lista.get(i);
			if(to.getId() == ((Filme) filme).getId()){
				return i;
			}
		}
		return -1;
	}

}

