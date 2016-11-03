(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_airlineDetailController', Com_airlineDetailController);

    Com_airlineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_airline', 'Cfdi'];

    function Com_airlineDetailController($scope, $rootScope, $stateParams, entity, Com_airline, Cfdi) {
        var vm = this;
        vm.com_airline = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_airlineUpdate', function(event, result) {
            vm.com_airline = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
