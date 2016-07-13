(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_airlineDetailController', Freecom_airlineDetailController);

    Freecom_airlineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_airline', 'Free_cfdi'];

    function Freecom_airlineDetailController($scope, $rootScope, $stateParams, entity, Freecom_airline, Free_cfdi) {
        var vm = this;
        vm.freecom_airline = entity;
        vm.load = function (id) {
            Freecom_airline.get({id: id}, function(result) {
                vm.freecom_airline = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_airlineUpdate', function(event, result) {
            vm.freecom_airline = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
