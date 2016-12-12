(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataacquiringcopscDetailController', Freecom_dataacquiringcopscDetailController);

    Freecom_dataacquiringcopscDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_dataacquiringcopsc', 'Freecom_acquiring_data'];

    function Freecom_dataacquiringcopscDetailController($scope, $rootScope, $stateParams, entity, Freecom_dataacquiringcopsc, Freecom_acquiring_data) {
        var vm = this;
        vm.freecom_dataacquiringcopsc = entity;
        vm.load = function (id) {
            Freecom_dataacquiringcopsc.get({id: id}, function(result) {
                vm.freecom_dataacquiringcopsc = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_dataacquiringcopscUpdate', function(event, result) {
            vm.freecom_dataacquiringcopsc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
