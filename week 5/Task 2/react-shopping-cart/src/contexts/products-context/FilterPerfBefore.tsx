import React, { useState } from 'react';
import { IProduct } from 'models';

// Mock generator for a large product list
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

const FilterPerfBefore: React.FC = () => {
  const [time, setTime] = useState<number | null>(null);
  const [resultCount, setResultCount] = useState<number | null>(null);

  const handleRun = () => {
    const products = generateLargeProductList();
    const filters = ['M', 'L', 'XL'];
    const start = performance.now();
    const filtered = oldFilterProducts(products, filters);
    const end = performance.now();
    setTime(end - start);
    setResultCount(filtered.length);
  };

  return (
    <div style={{ margin: 20, padding: 20, border: '1px solid #ccc' }}>
      <h3>Filter Performance (Before Optimization)</h3>
      <button onClick={handleRun}>Run Benchmark</button>
      {time !== null && (
        <div>
          <p>Time taken: <b>{time.toFixed(2)} ms</b></p>
          <p>Filtered products: <b>{resultCount}</b></p>
        </div>
      )}
    </div>
  );
};

export default FilterPerfBefore; 