(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_apawDetailController', Freecom_apawDetailController);

    Freecom_apawDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_apaw', 'Free_cfdi', 'C_well_type', 'C_acquired_title', 'C_features_work_piece'];

    function Freecom_apawDetailController($scope, $rootScope, $stateParams, entity, Freecom_apaw, Free_cfdi, C_well_type, C_acquired_title, C_features_work_piece) {
        var vm = this;
        vm.freecom_apaw = entity;
        vm.load = function (id) {
            Freecom_apaw.get({id: id}, function(result) {
                vm.freecom_apaw = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_apawUpdate', function(event, result) {
            vm.freecom_apaw = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
