import { useCallback, useRef } from 'react';

import { useProductsContext } from './ProductsContextProvider';
import { IProduct } from 'models';
import { getProducts } from 'services/products';

const useProducts = () => {
  const {
    isFetching,
    setIsFetching,
    products,
    setProducts,
    filters,
    setFilters,
  } = useProductsContext();

  const allProductsRef = useRef<IProduct[] | null>(null);

  const fetchProducts = useCallback(async () => {
    setIsFetching(true);
    const products = await getProducts();
    setIsFetching(false);
    setProducts(products);
    allProductsRef.current = products; // cache all products
  }, [setIsFetching, setProducts]);

  const filterProducts = useCallback((filters: string[]) => {
    setIsFetching(true);
    const start = performance.now();

    // Use cached products if available, else fetch
    const products = allProductsRef.current || [];
    let filteredProducts;

    if (filters && filters.length > 0) {
      const filterSet = new Set(filters);
      filteredProducts = products.filter((p: IProduct) =>
        p.availableSizes.some((size: string) => filterSet.has(size))
      );
    } else {
      filteredProducts = products;
    }

    setFilters(filters);
    setProducts(filteredProducts);
    setIsFetching(false);

    const end = performance.now();
    console.log(`[Performance] filterProducts took ${end - start} ms`);
  }, [setFilters, setProducts, setIsFetching]);

  return {
    isFetching,
    fetchProducts,
    products,
    filterProducts,
    filters,
  };
};

export default useProducts;
