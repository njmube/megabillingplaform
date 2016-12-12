(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_data_enajenanteDetailController', Freecom_data_enajenanteDetailController);

    Freecom_data_enajenanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_data_enajenante', 'Freecom_public_notaries'];

    function Freecom_data_enajenanteDetailController($scope, $rootScope, $stateParams, entity, Freecom_data_enajenante, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_data_enajenante = entity;
        vm.load = function (id) {
            Freecom_data_enajenante.get({id: id}, function(result) {
                vm.freecom_data_enajenante = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_data_enajenanteUpdate', function(event, result) {
            vm.freecom_data_enajenante = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
