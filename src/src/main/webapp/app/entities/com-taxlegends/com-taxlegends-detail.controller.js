(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_taxlegendsDetailController', Com_taxlegendsDetailController);

    Com_taxlegendsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_taxlegends', 'Cfdi'];

    function Com_taxlegendsDetailController($scope, $rootScope, $stateParams, entity, Com_taxlegends, Cfdi) {
        var vm = this;
        vm.com_taxlegends = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_taxlegendsUpdate', function(event, result) {
            vm.com_taxlegends = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
