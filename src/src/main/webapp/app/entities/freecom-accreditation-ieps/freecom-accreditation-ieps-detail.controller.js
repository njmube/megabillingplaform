(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_accreditation_iepsDetailController', Freecom_accreditation_iepsDetailController);

    Freecom_accreditation_iepsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_accreditation_ieps', 'C_tar', 'Free_cfdi'];

    function Freecom_accreditation_iepsDetailController($scope, $rootScope, $stateParams, entity, Freecom_accreditation_ieps, C_tar, Free_cfdi) {
        var vm = this;
        vm.freecom_accreditation_ieps = entity;
        vm.load = function (id) {
            Freecom_accreditation_ieps.get({id: id}, function(result) {
                vm.freecom_accreditation_ieps = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_accreditation_iepsUpdate', function(event, result) {
            vm.freecom_accreditation_ieps = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
