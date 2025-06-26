import axios from 'axios';
import { IGetProductsResponse } from 'models';

const isProduction = process.env.NODE_ENV === 'production';

// Helper: Retry mechanism for async functions
async function retry<T>(fn: () => Promise<T>, retries = 3, delay = 500): Promise<T> {
  let lastError;
  for (let attempt = 0; attempt < retries; attempt++) {
    try {
      return await fn();
    } catch (err) {
      lastError = err;
      if (attempt < retries - 1) {
        await new Promise(res => setTimeout(res, delay));
      }
    }
  }
  throw lastError;
}

export const getProducts = async () => {
  try {
    let response: IGetProductsResponse;
    if (isProduction) {
      // Retry network requests up to 3 times
      response = await retry(async () => {
        const res = await axios.get('https://react-shopping-cart-67954.firebaseio.com/products.json');
        // HTTP status code validation
        if (res.status < 200 || res.status >= 300) {
          throw new Error(`Unexpected response status: ${res.status}`);
        }
        return res;
      });
    } else {
      // Local require fallback (no retry needed)
      response = require('static/json/products.json');
    }
    const { products } = response.data || [];
    return products;
  } catch (error: any) {
    // Network error handling and user-friendly messages
    if (axios.isAxiosError(error)) {
      if (error.response) {
        // Server responded with a status outside 2xx
        throw new Error(`Failed to fetch products: ${error.response.status} ${error.response.statusText}`);
      } else if (error.request) {
        // No response received
        throw new Error('Network error: No response received from server. Please check your connection and try again.');
      } else {
        // Other axios error
        throw new Error(`Request error: ${error.message}`);
      }
    } else if (error instanceof Error) {
      throw new Error(`Unexpected error: ${error.message}`);
    } else {
      throw new Error('An unknown error occurred while fetching products.');
    }
  }
};
