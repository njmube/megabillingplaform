(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ineDetailController', Freecom_ineDetailController);

    Freecom_ineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_ine', 'Free_cfdi', 'C_committee_type', 'C_process_type'];

    function Freecom_ineDetailController($scope, $rootScope, $stateParams, entity, Freecom_ine, Free_cfdi, C_committee_type, C_process_type) {
        var vm = this;
        vm.freecom_ine = entity;
        vm.load = function (id) {
            Freecom_ine.get({id: id}, function(result) {
                vm.freecom_ine = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_ineUpdate', function(event, result) {
            vm.freecom_ine = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
