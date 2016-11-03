(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_product_keyDetailController', Com_product_keyDetailController);

    Com_product_keyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_product_key'];

    function Com_product_keyDetailController($scope, $rootScope, $stateParams, entity, Com_product_key) {
        var vm = this;
        vm.com_product_key = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_product_keyUpdate', function(event, result) {
            vm.com_product_key = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
