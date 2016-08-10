(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_product_keyDetailController', Freecom_product_keyDetailController);

    Freecom_product_keyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_product_key'];

    function Freecom_product_keyDetailController($scope, $rootScope, $stateParams, entity, Freecom_product_key) {
        var vm = this;

        vm.freecom_product_key = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_product_keyUpdate', function(event, result) {
            vm.freecom_product_key = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
