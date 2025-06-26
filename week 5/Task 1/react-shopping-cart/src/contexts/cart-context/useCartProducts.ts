import { useCartContext } from './CartContextProvider';
import useCartTotal from './useCartTotal';
import { ICartProduct } from 'models';
import { useCallback } from 'react';

const useCartProducts = () => {
  const { products, setProducts } = useCartContext();
  const { updateCartTotal } = useCartTotal();

  const updateQuantitySafely = useCallback((
    currentProduct: ICartProduct,
    targetProduct: ICartProduct,
    quantity: number
  ): ICartProduct => {
    if (currentProduct.id === targetProduct.id) {
      const newQuantity = currentProduct.quantity + quantity;
      // Prevent negative quantities
      if (newQuantity < 0) return currentProduct;
      
      return {
        ...currentProduct,
        quantity: newQuantity,
      };
    }
    return currentProduct;
  }, []);

  const addProduct = useCallback((newProduct: ICartProduct) => {
    if (!newProduct?.id || typeof newProduct.quantity !== 'number') {
      console.warn('Invalid product data:', newProduct);
      return;
    }

    setProducts((currentProducts: ICartProduct[]) => {
      // Find product in a single pass instead of using some() + map()
      const existingProductIndex = currentProducts.findIndex(
        (product: ICartProduct) => product.id === newProduct.id
      );

      if (existingProductIndex >= 0) {
        // Product exists - update quantity
        return currentProducts.map((product: ICartProduct, index: number) => 
          index === existingProductIndex
            ? updateQuantitySafely(product, newProduct, newProduct.quantity)
            : product
        );
      }

      // Product doesn't exist - add new one with new reference
      const updatedProducts = [...currentProducts, { ...newProduct }];
      updateCartTotal(updatedProducts);
      return updatedProducts;
    });
  }, [updateCartTotal, updateQuantitySafely]);

  const removeProduct = useCallback((productToRemove: ICartProduct) => {
    if (!productToRemove?.id) {
      console.warn('Invalid product to remove:', productToRemove);
      return;
    }

    setProducts((currentProducts: ICartProduct[]) => {
      const updatedProducts = currentProducts.filter(
        (product: ICartProduct) => product.id !== productToRemove.id
      );
      updateCartTotal(updatedProducts);
      return updatedProducts;
    });
  }, [updateCartTotal]);

  const increaseProductQuantity = useCallback((productToIncrease: ICartProduct) => {
    if (!productToIncrease?.id) {
      console.warn('Invalid product to increase:', productToIncrease);
      return;
    }

    setProducts((currentProducts: ICartProduct[]) => {
      const updatedProducts = currentProducts.map(
        (product: ICartProduct) => updateQuantitySafely(product, productToIncrease, 1)
      );
      updateCartTotal(updatedProducts);
      return updatedProducts;
    });
  }, [updateCartTotal, updateQuantitySafely]);

  const decreaseProductQuantity = useCallback((productToDecrease: ICartProduct) => {
    if (!productToDecrease?.id) {
      console.warn('Invalid product to decrease:', productToDecrease);
      return;
    }

    setProducts((currentProducts: ICartProduct[]) => {
      const updatedProducts = currentProducts.map(
        (product: ICartProduct) => updateQuantitySafely(product, productToDecrease, -1)
      );
      updateCartTotal(updatedProducts);
      return updatedProducts;
    });
  }, [updateCartTotal, updateQuantitySafely]);

  return {
    products,
    addProduct,
    removeProduct,
    increaseProductQuantity,
    decreaseProductQuantity,
  };
};

export default useCartProducts;
