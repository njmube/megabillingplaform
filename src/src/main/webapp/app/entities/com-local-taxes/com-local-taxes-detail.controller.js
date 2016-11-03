(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_taxesDetailController', Com_local_taxesDetailController);

    Com_local_taxesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_local_taxes', 'Cfdi'];

    function Com_local_taxesDetailController($scope, $rootScope, $stateParams, entity, Com_local_taxes, Cfdi) {
        var vm = this;
        vm.com_local_taxes = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_local_taxesUpdate', function(event, result) {
            vm.com_local_taxes = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
