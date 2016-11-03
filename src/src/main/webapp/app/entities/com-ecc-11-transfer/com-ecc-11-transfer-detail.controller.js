(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11_transferDetailController', Com_ecc_11_transferDetailController);

    Com_ecc_11_transferDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_ecc_11_transfer', 'Com_ecc_11_concept'];

    function Com_ecc_11_transferDetailController($scope, $rootScope, $stateParams, entity, Com_ecc_11_transfer, Com_ecc_11_concept) {
        var vm = this;
        vm.com_ecc_11_transfer = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_ecc_11_transferUpdate', function(event, result) {
            vm.com_ecc_11_transfer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
