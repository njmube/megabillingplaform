(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Category_productDetailController', Category_productDetailController);

    Category_productDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Category_product', 'Product'];

    function Category_productDetailController($scope, $rootScope, $stateParams, entity, Category_product, Product) {
        var vm = this;
        vm.category_product = entity;
        vm.load = function (id) {
            Category_product.get({id: id}, function(result) {
                vm.category_product = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:category_productUpdate', function(event, result) {
            vm.category_product = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
