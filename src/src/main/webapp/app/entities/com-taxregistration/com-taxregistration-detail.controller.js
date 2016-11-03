(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_taxregistrationDetailController', Com_taxregistrationDetailController);

    Com_taxregistrationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_taxregistration', 'Cfdi'];

    function Com_taxregistrationDetailController($scope, $rootScope, $stateParams, entity, Com_taxregistration, Cfdi) {
        var vm = this;
        vm.com_taxregistration = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_taxregistrationUpdate', function(event, result) {
            vm.com_taxregistration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
