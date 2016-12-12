(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataunenajenanteDetailController', Freecom_dataunenajenanteDetailController);

    Freecom_dataunenajenanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_dataunenajenante', 'Freecom_data_enajenante'];

    function Freecom_dataunenajenanteDetailController($scope, $rootScope, $stateParams, entity, Freecom_dataunenajenante, Freecom_data_enajenante) {
        var vm = this;
        vm.freecom_dataunenajenante = entity;
        vm.load = function (id) {
            Freecom_dataunenajenante.get({id: id}, function(result) {
                vm.freecom_dataunenajenante = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_dataunenajenanteUpdate', function(event, result) {
            vm.freecom_dataunenajenante = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
