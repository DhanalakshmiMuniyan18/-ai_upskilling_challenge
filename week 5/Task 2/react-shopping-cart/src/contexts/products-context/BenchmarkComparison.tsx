import React, { useState } from 'react';
import { IProduct } from 'models';

// Generate a large mock product list
function generateLargeProductList(count = 10000): IProduct[] {
  const sizes = ['S', 'M', 'L', 'XL', 'XXL'];
  return Array.from({ length: count }, (_, i) => ({
    id: i,
    title: `Product ${i}`,
    availableSizes: [sizes[i % sizes.length]],
    price: Math.random() * 100,
    currencyFormat: '$',
    currencyId: 'USD',
    installments: 1,
    isFreeShipping: true,
    sku: i,
    style: '',
    description: '',
  }));
}

// Before: Original (unoptimized) filter logic
function oldFilterProducts(products: IProduct[], filters: string[]): IProduct[] {
  if (filters && filters.length > 0) {
    return products.filter((p: IProduct) =>
      filters.find((filter: string) =>
        p.availableSizes.find((size: string) => size === filter)
      )
    );
  } else {
    return products;
  }
}

// After: Optimized filter logic
function optimizedFilterProducts(products: IProduct[], filters: string[]): IProduct[] {
  if (filters && filters.length > 0) {
    const filterSet = new Set(filters);
    return products.filter((p: IProduct) =>
      p.availableSizes.some((size: string) => filterSet.has(size))
    );
  } else {
    return products;
  }
}

const BenchmarkComparison: React.FC = () => {
  const [beforeTime, setBeforeTime] = useState<number | null>(null);
  const [afterTime, setAfterTime] = useState<number | null>(null);
  const [resultCount, setResultCount] = useState<number | null>(null);

  const handleRun = () => {
    const products = generateLargeProductList();
    const filters = ['M', 'L', 'XL'];

    // Before
    const startBefore = performance.now();
    const filteredBefore = oldFilterProducts(products, filters);
    const endBefore = performance.now();

    // After
    const startAfter = performance.now();
    const filteredAfter = optimizedFilterProducts(products, filters);
    const endAfter = performance.now();

    setBeforeTime(endBefore - startBefore);
    setAfterTime(endAfter - startAfter);
    setResultCount(filteredAfter.length);
  };

  return (
    <div style={{ margin: 20, padding: 20, border: '2px solid #1976d2', borderRadius: 8 }}>
      <h2>Filter Performance Comparison</h2>
      <button onClick={handleRun} style={{ marginBottom: 16 }}>Run Benchmark</button>
      <div style={{ display: 'flex', gap: 40 }}>
        <div>
          <h4>Before Optimization</h4>
          {beforeTime !== null && (
            <p>Time taken: <b>{beforeTime.toFixed(2)} ms</b></p>
          )}
        </div>
        <div>
          <h4>After Optimization</h4>
          {afterTime !== null && (
            <p>Time taken: <b>{afterTime.toFixed(2)} ms</b></p>
          )}
        </div>
      </div>
      {resultCount !== null && (
        <p style={{ marginTop: 16 }}>Filtered products: <b>{resultCount}</b></p>
      )}
    </div>
  );
};

export default BenchmarkComparison; 