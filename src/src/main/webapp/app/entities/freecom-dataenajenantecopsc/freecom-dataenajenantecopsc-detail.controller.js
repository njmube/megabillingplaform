(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataenajenantecopscDetailController', Freecom_dataenajenantecopscDetailController);

    Freecom_dataenajenantecopscDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_dataenajenantecopsc', 'Freecom_data_enajenante'];

    function Freecom_dataenajenantecopscDetailController($scope, $rootScope, $stateParams, entity, Freecom_dataenajenantecopsc, Freecom_data_enajenante) {
        var vm = this;
        vm.freecom_dataenajenantecopsc = entity;
        vm.load = function (id) {
            Freecom_dataenajenantecopsc.get({id: id}, function(result) {
                vm.freecom_dataenajenantecopsc = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_dataenajenantecopscUpdate', function(event, result) {
            vm.freecom_dataenajenantecopsc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
