(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_vehicle_customs_informationDetailController', Freecom_vehicle_customs_informationDetailController);

    Freecom_vehicle_customs_informationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_vehicle_customs_information', 'Freecom_used_vehicle'];

    function Freecom_vehicle_customs_informationDetailController($scope, $rootScope, $stateParams, entity, Freecom_vehicle_customs_information, Freecom_used_vehicle) {
        var vm = this;
        vm.freecom_vehicle_customs_information = entity;
        vm.load = function (id) {
            Freecom_vehicle_customs_information.get({id: id}, function(result) {
                vm.freecom_vehicle_customs_information = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_vehicle_customs_informationUpdate', function(event, result) {
            vm.freecom_vehicle_customs_information = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
