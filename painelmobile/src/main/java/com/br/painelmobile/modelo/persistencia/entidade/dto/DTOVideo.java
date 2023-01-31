package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Video;

public class DTOVideo implements Serializable{

	private static final long serialVersionUID = -8863989366948427335L;
	private List<Video> videos;
	
	
	public DTOVideo() {	
		
	}
	
	public DTOVideo(List<Video> videos) {	
		this.videos = videos;
	}

	
	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> video) {
		this.videos = video;
	}


	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((videos == null) ? 0 : videos.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOVideo other = (DTOVideo) obj;
		if (videos == null) {
			if (other.videos != null)
				return false;
		} else if (!videos.equals(other.videos))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
}
