(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_doneesDetailController', Freecom_doneesDetailController);

    Freecom_doneesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_donees', 'Free_cfdi'];

    function Freecom_doneesDetailController($scope, $rootScope, $stateParams, entity, Freecom_donees, Free_cfdi) {
        var vm = this;
        vm.freecom_donees = entity;
        vm.load = function (id) {
            Freecom_donees.get({id: id}, function(result) {
                vm.freecom_donees = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_doneesUpdate', function(event, result) {
            vm.freecom_donees = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
