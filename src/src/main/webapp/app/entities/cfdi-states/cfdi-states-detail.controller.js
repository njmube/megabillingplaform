(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_statesDetailController', Cfdi_statesDetailController);

    Cfdi_statesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cfdi_states'];

    function Cfdi_statesDetailController($scope, $rootScope, $stateParams, entity, Cfdi_states) {
        var vm = this;

        vm.cfdi_states = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:cfdi_statesUpdate', function(event, result) {
            vm.cfdi_states = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
