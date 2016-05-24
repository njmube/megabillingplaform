(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Product', 'Category_product'];

    function ProductDetailController($scope, $rootScope, $stateParams, entity, Product, Category_product) {
        var vm = this;
        vm.product = entity;
        vm.load = function (id) {
            Product.get({id: id}, function(result) {
                vm.product = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
