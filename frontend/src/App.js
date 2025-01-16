import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [city, setCity] = useState('');
  const [weather, setWeather] = useState(null);
  const [error, setError] = useState(null);

  const fetchWeather = async () => {
    setError(null);
    try {
      const response = await axios.get(`/api/weather/${city}`);
      setWeather(response.data);
    } catch (error) {
      console.error('Error fetching weather:', error);
      setWeather(null);
      if (error.response) {
        setError(error.response.data.message || 'An error occurred while fetching weather data');
      } else {
        setError('Unable to connect to the weather service. Please try again later.');
      }
    }
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter' && city.trim()) {
      fetchWeather();
    }
  };

  return (
    <div className="App">
      <h1>Weather Forecast</h1>
      <div className="search-container">
        <input
          type="text"
          value={city}
          onChange={(e) => setCity(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="🔍 Enter city name..."
        />
        <button onClick={fetchWeather} disabled={!city.trim()}>
          Search
        </button>
      </div>
      
      {error && (
        <div className="error-container">
          <div className="error-message">
            <span className="error-icon">⚠️</span>
            <p>{error}</p>
          </div>
        </div>
      )}
      
      {weather && (
        <div className="weather-info">
          <h2>{weather.city}</h2>
          <p>
            <span>🌡️ Temperature</span>
            <span>{weather.temperature}°C</span>
          </p>
          <p>
            <span>💧 Humidity</span>
            <span>{weather.humidity}%</span>
          </p>
          <p>
            <span>☁️ Condition</span>
            <span>{weather.description}</span>
          </p>
          <p>
            <span>🌪️ Wind Speed</span>
            <span>{weather.windSpeed} m/s</span>
          </p>
        </div>
      )}
    </div>
  );
}

export default App; 