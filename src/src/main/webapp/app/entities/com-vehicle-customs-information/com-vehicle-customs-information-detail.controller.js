(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_vehicle_customs_informationDetailController', Com_vehicle_customs_informationDetailController);

    Com_vehicle_customs_informationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_vehicle_customs_information', 'Com_used_vehicle'];

    function Com_vehicle_customs_informationDetailController($scope, $rootScope, $stateParams, entity, Com_vehicle_customs_information, Com_used_vehicle) {
        var vm = this;
        vm.com_vehicle_customs_information = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_vehicle_customs_informationUpdate', function(event, result) {
            vm.com_vehicle_customs_information = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
